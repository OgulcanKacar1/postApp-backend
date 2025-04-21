package com.example.postApp.services;

import com.example.postApp.entities.Comment;
import com.example.postApp.entities.Post;
import com.example.postApp.entities.User;
import com.example.postApp.repos.CommentRepository;
import com.example.postApp.requests.CommendUpdateRequest;
import com.example.postApp.requests.CommentCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent()) {
            return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            return commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        } else {
            return commentRepository.findAll();
        }
    }

    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());
        if (user != null && post != null) {
            Comment comment = new Comment();
            comment.setId(request.getId());
            comment.setText(request.getText());
            comment.setUser(user);
            comment.setPost(post);
            return commentRepository.save(comment);
        }else
            return null;
    }

    public Comment updateOneCommentById(Long commentId, CommendUpdateRequest request) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment toUpdate = comment.get();
            toUpdate.setText(request.getText());

            return commentRepository.save(toUpdate);
        }else
            return null;
    }
}
