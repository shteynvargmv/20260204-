package com.example.demo.dto;
import java.util.List;

public class AssetDto {
    private String uid;
    private String type;
    private String name;
    private String nameBrief;
    private String description;
    private List<String> requiredTests;
    private String gosRegCode;
    private String cfi;
    private String codeNsd;
    private String status;
    private String updatedAt;
    private String brCode;
    private String brCodeName;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNameBrief() { return nameBrief; }
    public void setNameBrief(String nameBrief) { this.nameBrief = nameBrief; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getRequiredTests() { return requiredTests; }
    public void setRequiredTests(List<String> requiredTests) { this.requiredTests = requiredTests; }

    public String getGosRegCode() { return gosRegCode; }
    public void setGosRegCode(String gosRegCode) { this.gosRegCode = gosRegCode; }

    public String getCfi() { return cfi; }
    public void setCfi(String cfi) { this.cfi = cfi; }

    public String getCodeNsd() { return codeNsd; }
    public void setCodeNsd(String codeNsd) { this.codeNsd = codeNsd; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public String getBrCode() { return brCode; }
    public void setBrCode(String brCode) { this.brCode = brCode; }

    public String getBrCodeName() { return brCodeName; }
    public void setBrCodeName(String brCodeName) { this.brCodeName = brCodeName; }

    @Override
    public String toString() {
        return "AssetDto{" +
                "uid='" + uid + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", nameBrief='" + nameBrief + '\'' +
                ", description='" + description + '\'' +
                ", requiredTests=" + requiredTests +
                ", gosRegCode='" + gosRegCode + '\'' +
                ", cfi='" + cfi + '\'' +
                ", codeNsd='" + codeNsd + '\'' +
                ", status='" + status + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", brCode='" + brCode + '\'' +
                ", brCodeName='" + brCodeName + '\'' +
                '}';
    }
}