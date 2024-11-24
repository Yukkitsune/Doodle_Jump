import androidx.recyclerview.widget.DiffUtil
import com.example.doodlejump.data.HighScore

class HighScoresDiffUtil : DiffUtil.ItemCallback<HighScore>() {
    override fun areItemsTheSame(oldItem: HighScore, newItem: HighScore): Boolean {
        return oldItem.playerName == newItem.playerName
    }

    override fun areContentsTheSame(oldItem: HighScore, newItem: HighScore): Boolean {
        return oldItem == newItem
    }
}