package KU.GraduationProject.BasicServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class imageFileDto {

    private String fileName;

    private String fileDownloadUri;

    private String fileType;

    private long size;

    private long imageFileId;

}
