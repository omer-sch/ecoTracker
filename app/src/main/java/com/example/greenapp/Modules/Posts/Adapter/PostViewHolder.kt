package com.example.greenapp.Modules.Posts.Adapter;


import com.example.greenapp.Model.Post
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.greenapp.Modules.Posts.PostsRecyclerViewActivity
import com.example.greenapp.R

class PostViewHolder (
    val itemView:View,
    val listener: PostsRecyclerViewActivity.OnItemClickListener?,
    var posts:List<Post>?): RecyclerView.ViewHolder(itemView) {

    var nameTextView: TextView? = null
   // var idTextView: TextView? = null
    var postCheckbox: CheckBox? = null
    var post: Post? = null

    init {
        nameTextView = itemView.findViewById(R.id.tvPostListRowName)
       // idTextView = itemView.findViewById(R.id.tvPostListRowID)
        postCheckbox = itemView.findViewById(R.id.cbPostListRow)

        postCheckbox?.setOnClickListener {
            val student = posts?.get(adapterPosition)
            student?.isChecked = postCheckbox?.isChecked ?: false
        }

        itemView.setOnClickListener {
            Log.i("TAG", "StudentViewHolder: Position clicked $adapterPosition")

            listener?.onItemClick(adapterPosition)
            listener?.onStudentClicked(post)
        }
    }

    fun bind(post: Post?) {
        this.post = post
        nameTextView?.text = post?.name
        // idTextView?.text = post?.id
        postCheckbox?.apply {
            isChecked = post?.isChecked ?: false
        }
    }
}
