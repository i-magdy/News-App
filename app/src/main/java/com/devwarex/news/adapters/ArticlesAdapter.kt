package com.devwarex.news.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devwarex.news.R
import com.devwarex.news.db.ArticleRelation

class ArticlesAdapter(
    val listener: ArticleListener
): RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    private var articles: List<ArticleRelation> = emptyList()

    inner class ArticlesViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) , View.OnClickListener{
        private val glide = Glide.with(view.context)
        private val title = view.findViewById<TextView>(R.id.article_title_tv)
        private val description = view.findViewById<TextView>(R.id.article_description_tv)
        private val imgView = view.findViewById<ImageView>(R.id.article_iv)
        private val author = view.findViewById<TextView>(R.id.article_author)
        private val category = view.findViewById<TextView>(R.id.category_title_tv)
        private val bookmark = view.findViewById<ImageView>(R.id.bookmark_iv)
        private val dateTv = view.findViewById<TextView>(R.id.article_date_tv)

        init {
            view.setOnClickListener(this)
            bookmark.setOnClickListener(this)
        }

        fun onBind(article: ArticleRelation){

        }

        override fun onClick(v: View?) {
            val article = articles[adapterPosition]
            if (v != null && v.id == R.id.bookmark_iv){
                listener.bookmarkArticle(
                    articleUrl = article.article.url,
                    isBooked = !article.article.isBooked
                )
            }else{
                listener.onArticleClick(article.article.url)
            }
        }
    }

    interface ArticleListener{
        fun onArticleClick(articleUrl: String)
        fun bookmarkArticle(articleUrl: String,isBooked: Boolean)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArticles(articles: List<ArticleRelation>){
        this.articles = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder = ArticlesViewHolder(
        view = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item,parent,false)
    )

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.onBind(articles[position])
    }

    override fun getItemCount(): Int =  articles.size
}