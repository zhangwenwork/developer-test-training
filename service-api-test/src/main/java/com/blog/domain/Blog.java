package com.blog.domain;

import com.blog.domain.exceptions.NoNeedToPublishException;

import java.time.Instant;
import java.util.UUID;

public class Blog{
    private final UUID id;
    private String title;
    private String body;
    private final UUID authorId;
    private Status status;
    private final Instant createdAt;
    private Instant savedAt;
    private PublishedBlog published;

    Blog(String title, String body, UUID authorId) {
        validateTitle(title);
        validateAuthor(authorId);

        this.id = UUID.randomUUID();
        this.title = title;
        this.body = body;
        this.authorId = authorId;
        this.status = Status.Draft;
        this.savedAt = this.createdAt = Instant.now();
    }

    public Blog(UUID id, String title, String body,
                UUID authorId, Status status, Instant createdAt, Instant savedAt, PublishedBlog published) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.authorId = authorId;
        this.status = status;
        this.createdAt = createdAt;
        this.savedAt = savedAt;
        this.published = published;
    }

    void publish() {
        validateIsNeedToPublish();

        this.published = new PublishedBlog(this.title, this.body, Instant.now());
        this.status = Status.Published;
    }

    void saveDraft(String title, String body) {
        validateTitle(title);

        this.title = title;
        this.body = body;
        this.savedAt = Instant.now();
    }

    boolean isPublished() {
        return this.published != null;
    }

    private void validateAuthor(UUID author) {
        if (author == null) {
            throw new IllegalArgumentException("the author cannot be null");
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("the title cannot be null or no content");
        }
    }

    private void validateIsNeedToPublish() {
        if (this.status == Status.Published) {
            boolean noChange =
                    this.title.equals(this.published.getTitle()) && this.body.equals(this.published.getBody());
            if (noChange) {
                throw new NoNeedToPublishException();
            }
        }
    }

    public enum Status{
        Draft,
        Published
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public Status getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getSavedAt() {
        return savedAt;
    }

    public PublishedBlog getPublished() {
        return published;
    }
}
