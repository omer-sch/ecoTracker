package com.example.greenapp.Modules.Posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greenapp.Model.Model
import com.example.greenapp.Model.Post
import com.example.greenapp.Modules.Posts.Adapter.PostsRecyclerAdapter
import com.example.greenapp.R
import com.example.greenapp.databinding.FragmentPostsBinding


class PostsFragment : Fragment() {

    var postsRcyclerView: RecyclerView? = null
    var posts: List<Post>? = null
    var adapter: PostsRecyclerAdapter? = null
    var progressBar: ProgressBar? = null

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        val view = binding.root

        progressBar = binding.progressBar

        progressBar?.visibility = View.VISIBLE

        Model.instance.getAllPosts { posts ->
            this.posts = posts
            adapter?.posts = posts
            adapter?.notifyDataSetChanged()

            progressBar?.visibility = View.GONE
        }

        postsRcyclerView = binding.rvPostsFragmentList
        postsRcyclerView?.setHasFixedSize(true)
        postsRcyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = PostsRecyclerAdapter(posts)
        adapter?.listener = object : PostsRecyclerViewActivity.OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "StudentsRecyclerAdapter: Position clicked $position")
                val post = posts?.get(position)
//                post?.let {
//                    val action = PostsFragmentDirections.actionStudentsFragmentToBlueFragment(it.name)
//                    Navigation.findNavController(view).navigate(action)
//                }
            }

            override fun onStudentClicked(post: Post?) {
                Log.i("TAG", "POST $post")
            }
        }

        postsRcyclerView?.adapter = adapter

        val addPostButton: ImageButton = view.findViewById(R.id.ibtnPostsFragmentAddStudent)
       // val action = Navigation.createNavigateOnClickListener(PostsFragmentDirections.actionGlobalAddStudentFragment())
       // addPostButton.setOnClickListener(action)

        return view
    }

    override fun onResume() {
        super.onResume()

        progressBar?.visibility = View.VISIBLE

        Model.instance.getAllPosts { posts ->
            this.posts = posts
            adapter?.posts = posts
            adapter?.notifyDataSetChanged()

            progressBar?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }


}