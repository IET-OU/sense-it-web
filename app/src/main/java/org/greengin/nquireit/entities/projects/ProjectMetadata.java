package org.greengin.nquireit.entities.projects;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.OrderColumn;
import java.util.List;

@Embeddable
public class ProjectMetadata {
    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    String teaser;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    String outline;

    @Getter
    @Setter
    String image;

    @ElementCollection
    @OrderColumn
    @Getter
    @Setter
    List<ProjectMetadataBlock> blocks;
}
