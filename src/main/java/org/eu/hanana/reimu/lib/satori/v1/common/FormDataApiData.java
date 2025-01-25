package org.eu.hanana.reimu.lib.satori.v1.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.eu.hanana.reimu.lib.satori.v1.protocol.IUserId;
import org.jetbrains.annotations.Nullable;
import reactor.netty.http.client.HttpClientForm;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class FormDataApiData extends DefaultApiData{
    public String boundary = "boundary";
    protected final List<FormDataEntity> formDataEntities = new ArrayList<>();
    public void putFile(FormDataEntity entity){
        formDataEntities.add(entity);
    }
    @Override
    public Tuple2<Map<String,String>,String> getToSendData() {
        httpHeader.put("Content-Type","multipart/form-data; boundary="+boundary);
        var sb = new StringBuilder();
        for (FormDataEntity formDataEntity : formDataEntities) {
            sb.append("--").append(boundary).append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"").append(formDataEntity.name).append("\"; filename=\"").append(formDataEntity.filename).append("\"\r\n");
            sb.append("Content-Type: ").append(formDataEntity.contentType).append("\r\n").append("\r\n");
            sb.append(new String(formDataEntity.data, StandardCharsets.UTF_8));
        }
        this.httpBody=sb.append("--").append(boundary).append("--").toString();
        return super.getToSendData();
    }

    @Override
    public void loadFromJson(Tuple2<Map<String,String>,String> data) {
        httpHeader.clear();
        httpHeader=data.getT1();
        httpBody=data.getT2();
    }
    public static record FormDataEntity(String contentType,String name,@Nullable String filename,byte[] data){}
}
