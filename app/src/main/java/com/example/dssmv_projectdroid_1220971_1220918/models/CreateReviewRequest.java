package com.example.dssmv_projectdroid_1220971_1220918.models;

public class CreateReviewRequest {
    private boolean recommended;
    private String review;

    public CreateReviewRequest(boolean recommended, String review) {
        this.recommended = recommended;
        this.review = review;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
