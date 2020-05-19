package com.rk.stackdevs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public final class Dev {

    @Expose
    @SerializedName("user_id")
    private long id;

    @Expose
    @SerializedName("account_id")
    private long accountId;

    @Expose
    @SerializedName("display_name")
    private String name;

    @Expose
    @SerializedName("profile_image")
    private String photoUrl;

    @Expose
    @SerializedName("link")
    private String profileUrl;

    @Expose
    @SerializedName("website_url")
    private String webUrl;

    @Expose
    @SerializedName("location")
    private String location;

    @Expose
    @SerializedName("reputation")
    private long reputationScore;

    @Expose
    @SerializedName("badge_counts")
    private Badge badge;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getReputationScore() {
        return reputationScore;
    }

    public void setReputationScore(long reputationScore) {
        this.reputationScore = reputationScore;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dev dev = (Dev) o;
        return id == dev.id &&
                accountId == dev.accountId &&
                reputationScore == dev.reputationScore &&
                Objects.equals(name, dev.name) &&
                Objects.equals(photoUrl, dev.photoUrl) &&
                Objects.equals(profileUrl, dev.profileUrl) &&
                Objects.equals(webUrl, dev.webUrl) &&
                Objects.equals(location, dev.location) &&
                Objects.equals(badge, dev.badge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, name, photoUrl, profileUrl, webUrl, location, reputationScore, badge);
    }

    @Override
    public String toString() {
        return "Dev{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", location='" + location + '\'' +
                ", reputationScore=" + reputationScore +
                ", badge=" + badge +
                '}';
    }
}
