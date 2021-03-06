package org.greengin.nquireit.dao;

import org.greengin.nquireit.entities.rating.Comment;
import org.greengin.nquireit.entities.rating.CommentThreadEntity;
import org.greengin.nquireit.entities.users.UserProfile;
import org.greengin.nquireit.logic.rating.CommentRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class CommentsDao {
    private static final String VOTE_QUERY = "SELECT v FROM Vote v WHERE v.thread = :thread AND v.user = :user";

    @PersistenceContext
    EntityManager em;


    public Comment getComment(CommentThreadEntity thread, Long commentId) {
        if (thread != null && commentId != null) {
            Comment c = em.find(Comment.class, commentId);

            if (c != null && thread.equals(c.getTarget())) {
                return c;
            }
        }

        return null;
    }

    @Transactional
    public Comment comment(UserProfile user, CommentThreadEntity target, CommentRequest request) {
        em.persist(target);
        Comment comment = commentWithinTransaction(user, target, request);
        em.persist(comment);
        return comment;
    }

    public Comment commentWithinTransaction(UserProfile user, CommentThreadEntity target, CommentRequest request) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setTarget(target);
        target.getComments().add(comment);
        request.update(comment);
        return comment;
    }

    @Transactional
    public boolean deleteComment(UserProfile user, CommentThreadEntity target, Long commentId) {
        if (user != null && target != null && commentId != null) {
            em.persist(target);
            Comment c = em.find(Comment.class, commentId);

            if (c != null && user.equals(c.getUser()) && target.equals(c.getTarget())) {
                target.getComments().remove(c);
                em.remove(c);
                return true;
            }
        }

        return false;
    }
}
