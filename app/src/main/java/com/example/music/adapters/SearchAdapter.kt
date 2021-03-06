package com.example.music.adapters

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.music.R
import com.example.music.data.Songs
import com.example.music.fragments.SongPlayingFragment

class SearchAdapter(_songDetails: ArrayList<Songs>, _context: Context?) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    var songDetails: ArrayList<Songs>? = null
    var mContext: Context? = null

    init {
        this.songDetails = _songDetails
        this.mContext = _context
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val songObject = songDetails?.get(position)
        if (songObject?.artist.equals("<unknown>", ignoreCase = true)) {
            holder.trackArtist?.text = "unknown"
        } else {
            holder.trackArtist?.text = songObject?.artist
        }

        holder.trackTitle?.text = songObject?.songTitle
        holder.contentHolder?.setOnClickListener {

            try {
                if (SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean) {
                    SongPlayingFragment.Statified.mediaPlayer?.stop()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val songPlayingFragment = SongPlayingFragment()
            val args = Bundle()
            args.putString("path", songObject?.songData)
            args.putString("songTitle", songObject?.songTitle)
            args.putString("songArtist", songObject?.artist)
            args.putInt("songPosition", position)
            args.putInt("SongId", songObject?.songID?.toInt() as Int)
            args.putParcelableArrayList("songsData", songDetails)
            songPlayingFragment.arguments = args
            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.flContent, songPlayingFragment)
                .addToBackStack("SongPlayingFragment")
                .commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (songDetails == null) {
            return 0
        } else {
            return (songDetails as ArrayList<Songs>).size
        }

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var trackTitle: TextView? = null
        var trackArtist: TextView? = null
        var contentHolder: ConstraintLayout? = null

        init {
            trackTitle = view.findViewById(R.id.song_name) as TextView
            trackArtist = view.findViewById(R.id.song_name) as TextView
            contentHolder = view.findViewById(R.id.contentRow) as ConstraintLayout

        }
    }
}