package com.lectomundo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Properties;

public class BackBlazeUploader {


    private static final String nombre_deposito = "BibliotecaVirtual";
    private static final String url_backblaze = "https://api.backblazeb2.com";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String keyId;
    private String appKey;

    private String accountId;
    private String apiUrl;
    private String authToken;
    private String bucketId;
    private String downloadUrl;

    public BackBlazeUploader() throws Exception {

        authorizeAccount();
        getBucketId();
    }

    private void authorizeAccount() throws Exception {

        Properties props = new Properties();

        // Ruta del archivo de configuración
        FileInputStream fis = new FileInputStream("config.properties");

        props.load(fis);
        this.keyId = props.getProperty("BACKBLAZE_KEY_ID");
        this.appKey = props.getProperty("BACKBLAZE_APP_KEY");

        String credentials = keyId + ":" + appKey;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

        Request request = new Request.Builder().url(url_backblaze + "/b2api/v2/b2_authorize_account").header("Authorization", "Basic " + encoded).build();

        try (Response response = client.newCall(request).execute()) {

            String jsonString = response.body().string();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            this.apiUrl = jsonNode.get("apiUrl").asText();
            this.authToken = jsonNode.get("authorizationToken").asText();
            this.accountId = jsonNode.get("accountId").asText();
            this.downloadUrl = jsonNode.get("downloadUrl").asText();
        }
    }

    private void getBucketId() throws Exception {

        RequestBody body = RequestBody.create("{\"accountId\":\"" + accountId + "\"}", MediaType.parse("application/json"));

        Request request = new Request.Builder().url(apiUrl + "/b2api/v2/b2_list_buckets").header("Authorization", authToken).post(body).build();

        try (Response response = client.newCall(request).execute()) {

            String jsonString = response.body().string();

            if (!response.isSuccessful()) {
                throw new IOException("Error al obtener buckets: " + jsonString);
            }

            JsonNode json = objectMapper.readTree(jsonString);
            JsonNode buckets = json.get("buckets");

            if (buckets == null || !buckets.isArray()) {
                throw new IllegalStateException("Respuesta no contiene el array 'buckets': " + jsonString);
            }
            System.out.println("Buckets disponibles");

            for (JsonNode bucket : buckets) {
                String nombre = bucket.get("bucketName").asText();
                String id = bucket.get("bucketId").asText();
                System.out.println("Nombre: " + nombre + " | ID: " + id);

                if (nombre.equals(nombre_deposito)) {
                    this.bucketId = id;
                    return;
                }
            }

            throw new IllegalArgumentException("Bucket no encontrado: " + nombre_deposito);
        }
    }

    public String uploadFile(File file, String fileName, String mimeType) throws Exception {

        RequestBody getUploadUrlBody = RequestBody.create("{\"bucketId\":\"" + bucketId + "\"}", MediaType.parse("application/json"));

        Request getUploadUrlRequest = new Request.Builder().url(apiUrl + "/b2api/v2/b2_get_upload_url").header("Authorization", authToken).post(getUploadUrlBody).build();

        String uploadUrl;
        String uploadAuthToken;

        try (Response response = client.newCall(getUploadUrlRequest).execute()) {

            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            uploadUrl = jsonNode.get("uploadUrl").asText();
            uploadAuthToken = jsonNode.get("authorizationToken").asText();
        }

        byte[] fileBytes = Files.readAllBytes(file.toPath());
        String sha1 = org.apache.commons.codec.digest.DigestUtils.sha1Hex(fileBytes);

        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        Request uploadRequest = new Request.Builder().url(uploadUrl).header("Authorization", uploadAuthToken).header("X-Bz-File-Name", encodedFileName).header("Content-Type", mimeType).header("X-Bz-Content-Sha1", sha1).post(RequestBody.create(fileBytes)).build();

        try (Response response = client.newCall(uploadRequest).execute()) {

            if (!response.isSuccessful()) {

                String errorBody = response.body() != null ? response.body().string() : "Respuesta vacía";
                throw new IOException("Error al subir archivo: " + errorBody);
            }

            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            return jsonNode.get("fileName").asText();
        }
    }

    public String getPublicUrl(String fileName) {

        String[] partes = fileName.split("/");
        StringBuilder pathCodificado = new StringBuilder();

        for (int i = 0; i < partes.length; i++) {

            pathCodificado.append(URLEncoder.encode(partes[i], StandardCharsets.UTF_8));
            if (i < partes.length - 1) {

                pathCodificado.append("/");
            }
        }

        return downloadUrl + "/file/" + nombre_deposito + "/" + pathCodificado;
    }

}
