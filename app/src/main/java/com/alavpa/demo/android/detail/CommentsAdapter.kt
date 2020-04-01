package com.alavpa.demo.android.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alavpa.demo.R
import com.alavpa.demo.android.detail.writer.WriterManager
import com.alavpa.demo.domain.model.Comment

class CommentsAdapter(
    private val context: Context,
    private val writerManager: WriterManager,
    private val comments: MutableList<Comment> = mutableListOf()
) : RecyclerView.Adapter<CommentsAdapter.CommentItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItem {
        return LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false).let { CommentItem(it) }
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentItem, position: Int) {
        holder.bind(comments[position], writerManager)
    }

    fun addNewItems(comments: List<Comment>) {
        this.comments.clear()
        this.comments.addAll(comments)
        this.notifyDataSetChanged()
    }

    class CommentItem(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        private val tvEmail = itemView.findViewById<TextView>(R.id.tv_email)
        private val tvBody = itemView.findViewById<TextView>(R.id.tv_body)
        fun bind(comment: Comment, writerManager: WriterManager) {
            tvTitle?.text = comment.title
            tvEmail?.text = writerManager.write(comment.email)
            tvBody?.text = comment.body
        }
    }
}
