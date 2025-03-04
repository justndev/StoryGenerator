package com.ndev.storyGeneratorBackend.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StoryRequestDTO {
    private String bgVideoId;
    private String text;

    public String getBgVideoId() {
        return bgVideoId;
    }
    public void setBgVideoId(String bgVideoId) {
        this.bgVideoId = bgVideoId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

}
