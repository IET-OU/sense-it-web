package org.greengin.nquireit.entities.rating;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Vector;

@Entity
public class ForumNode extends VotableEntity {

    @Getter
    @Setter
    String title;

    @Getter
    @Setter
    String description;

    @ManyToOne
    @Getter
    @Setter
    ForumNode parent = null;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Getter
    @Setter
    List<ForumNode> children = new Vector<ForumNode>();

    @OneToMany(mappedBy = "forum", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Getter
    @Setter
    List<ForumThread> threads = new Vector<ForumThread>();

    @Basic
    @Getter
    int threadCount = 0;

    @Basic
    @Getter
    int commentCount = 0;

    @ManyToOne
    @Getter
    ForumThread lastPost = null;

    public void updateLastPost(ForumThread thread) {
        this.lastPost = thread;
        this.threadCount = threads.size();
        int n = 0;
        for (ForumThread t : threads) {
            n += thread.getCommentCount();
        }
        this.commentCount = n;
    }
}
