package com.jd.jr.tradeCenter.model;

import java.math.BigDecimal;
import java.util.Date;

public class FinanceAssets extends BaseModel {
    private String id;

    private String assetName;

    private String assetTransferId;

    private String assetTransfeeId;

    private BigDecimal listingPrice;

    private BigDecimal deposit;

    private Date listingStartDate;

    private Date listingEndDate;

    private String status;

    private String assetType;

    private Date crateDate;

    private Date updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName == null ? null : assetName.trim();
    }

    public String getAssetTransferId() {
        return assetTransferId;
    }

    public void setAssetTransferId(String assetTransferId) {
        this.assetTransferId = assetTransferId == null ? null : assetTransferId.trim();
    }

    public String getAssetTransfeeId() {
        return assetTransfeeId;
    }

    public void setAssetTransfeeId(String assetTransfeeId) {
        this.assetTransfeeId = assetTransfeeId == null ? null : assetTransfeeId.trim();
    }

    public BigDecimal getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(BigDecimal listingPrice) {
        this.listingPrice = listingPrice;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Date getListingStartDate() {
        return listingStartDate;
    }

    public void setListingStartDate(Date listingStartDate) {
        this.listingStartDate = listingStartDate;
    }

    public Date getListingEndDate() {
        return listingEndDate;
    }

    public void setListingEndDate(Date listingEndDate) {
        this.listingEndDate = listingEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public Date getCrateDate() {
        return crateDate;
    }

    public void setCrateDate(Date crateDate) {
        this.crateDate = crateDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "FinanceAssets{" +
                "id='" + id + '\'' +
                ", assetName='" + assetName + '\'' +
                ", assetTransferId='" + assetTransferId + '\'' +
                ", assetTransfeeId='" + assetTransfeeId + '\'' +
                ", listingPrice=" + listingPrice +
                ", deposit=" + deposit +
                ", listingStartDate=" + listingStartDate +
                ", listingEndDate=" + listingEndDate +
                ", status=" + status +
                ", assetType=" + assetType +
                ", crateDate=" + crateDate +
                ", updateDate=" + updateDate +
                '}';
    }
}