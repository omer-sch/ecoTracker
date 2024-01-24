package com.example.greenapp.Modules.Posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.greenapp.R
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.net.toUri
import com.example.greenapp.Model.Model
import com.example.greenapp.Model.Post
import com.squareup.picasso.Picasso

class PostsListActivity : AppCompatActivity() {
    var postsListView: ListView? = null
    var posts: List<Post>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_list)

//        Model.instance.getAllPosts { posts ->
//            this.posts = posts
//        }

        postsListView = findViewById(R.id.lvPostList)
        postsListView?.adapter = PostsListAdapter(posts)

        postsListView?.setOnItemClickListener { parent, view, position, id ->
            Log.i("TAG", "Row was clicked at: $position")
        }
    }

    class PostsListAdapter(val posts: List<Post>?): BaseAdapter() {

        override fun getCount(): Int = posts?.size ?: 0

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val post = posts?.get(position)
            var view: View? = null
            if (convertView == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.post_layout_row, parent, false)
                val postCheckbox: CheckBox? = view?.findViewById(R.id.cbPostListRow)
                postCheckbox?.setOnClickListener {

                    (postCheckbox?.tag as? Int)?.let {tag ->
                        var post = posts?.get(tag)
                        post?.isChecked = postCheckbox?.isChecked ?: false
                    }
                }
            }

            view = view ?: convertView

            val nameTextView: TextView? = view?.findViewById(R.id.tvPostListRowName)
            val avatar: ImageView? =view?.findViewById(R.id.ivPostListRowAvatar)
            //val idTextView: TextView? = view?.findViewById(R.id.tvPostListRowID)
            val postCheckbox: CheckBox? = view?.findViewById(R.id.cbPostListRow)

            nameTextView?.text = post?.name
            Picasso.get().load(post?.uri?.toUri()).resize(1000, 1000).centerInside().into(avatar)
           // idTextView?.text = post?.id
            postCheckbox?.apply {
                isChecked = post?.isChecked ?: false
                tag = position
            }

            return view!!
        }

        override fun getItemId(position: Int): Long = 0
    }
}


