package com.lectomundo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class BackBlazeUploader {

    private static final String id_cuenta= "005da0be82a0ebf0000000001";
    private static final String llave_aplicacion="K005EPTrizZx+uZKRwTWKqp9NmSw674";
    private static final String nombre_deposito="BibliotecaVirtual";
    private static final String url_backblaze = "https://api.backblazeb2.com";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String apiUrl;
    private String authToken;
    private String bucketId;

    public BackblazeUploader() throws Exception{

        authorizeAccount();
        getBucketId();
    }

    private void authorizeAccount() throws Exception{

        String credentials = id_cuenta + ":" + llave_aplicacion;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

        Request request = new Request.Builder().url(url_backblaze + "/b2api/v2/b2_authorize_account").header("Authorization", "Basic "+ encoded).build();

        try(Response response = client.newCall(request).execute()){

            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            this.apiUrl = jsonNode.get("apiUrl").asText();
            this.authToken = jsonNode.get("authorizationToken").asText();
        }
    }

    private void getBucketId()throws Exception{

        RequestBody body = RequestBody.create("{\"accountId\":\""+ id_cuenta + "\"}",MediaType.parse("application/json"));

        Request request = new Request.Builder().url(apiUrl + "/b2api/v2/b2_list_buckets").header("Authorization", authToken).post(body).build();

        try(Response response = client.newCall(request).execute()){

            JsonNode json = objectMapper.readTree(response.body().string());

            for(JsonNode bucket : json.get("buckets")){

                if(bucket.get("bucketName").asText().equals(nombre_deposito)){

                    this.bucketId = bucket.get("bucketId").asText();
                    return;
                }
            }
            throw new IllegalArgumentException("Bucket no encontrado: " + nombre_deposito);
        }
    }

    public String uploadFile(File file, String fileName, String mimeType) throws Exception{

        RequestBody getUploadUrlBody = RequestBody.create("{\"bucketId\":\"" + bucketId + "\"}", MediaType.parse("application/json"));

        Request getUploadUrlRequest = new Request.Builder().url(apiUrl + "/b2api/v2/b2_get_upload_url").header("Authorization", authToken).post(getUploadUrlBody).build();

        String uploadUrl;
        String uploadAuthToken;

        try(Response response = client.newCall(getUploadUrlRequest).execute()){

            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            uploadUrl = jsonNode.get("uploadUrl").asText();
            uploadAuthToken = jsonNode.get("authorizationToken").asText();
        }

        byte[] fileBytes = Files.readAllBytes(file.toPath());
        String sha1 = org.apache.commons.codec.digest.DigestUtils.sha1Hex(fileBytes);

        Request uploadRequest = new Request.Builder().url(uploadUrl).header("Authorization", uploadAuthToken).header("X-Bz-File-Name", fileName).header("Content-Type", mimeType).header("X-Bz-Content-Sha1", sha1).post(RequestBody.create(fileBytes)).build();

        try(Response response = client.newCall(uploadRequest).execute()){

            if(!response.isSuccessful()){

                throw new IOException("Error al subir archivo: " + response.message());
            }

            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            return jsonNode.get("fileName").asText();
        }
    }

    public String getPublicUrl(String fileName){

        return "https://f000.backblazeb2.com/file/" + nombre_deposito + "/" + fileName;
    }

}
