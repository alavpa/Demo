package com.alavpa.demo.android.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alavpa.demo.R
import com.alavpa.demo.domain.model.Post

class PostAdapter(
    private val context: Context,
    private val posts: MutableList<Post> = mutableListOf(),
    private val onClickPost: (Post) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItem {
        return LayoutInflater.from(context).inflate(R.layout.item_post, parent, false).let { PostItem(it) }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostItem, position: Int) {
        holder.bind(posts[position])
        holder.itemView.setOnClickListener { onClickPost(posts[position]) }
    }

    fun addNewItems(posts: List<Post>) {
        this.posts.clear()
        this.posts.addAll(posts)
        this.notifyDataSetChanged()
    }

    class PostItem(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        fun bind(post: Post) {
            tvTitle?.text = post.title
        }
    }
}
