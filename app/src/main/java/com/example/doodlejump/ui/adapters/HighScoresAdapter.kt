import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doodlejump.data.HighScore
import com.example.doodlejump.databinding.ItemHighScoreBinding

class HighScoresAdapter :
    ListAdapter<HighScore, HighScoresAdapter.HighScoreViewHolder>(HighScoresDiffUtil()) {

    inner class HighScoreViewHolder(private val binding: ItemHighScoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(highScore: HighScore) {
            binding.tvPlayerName.text = highScore.playerName
            binding.tvScore.text = highScore.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        val binding =
            ItemHighScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HighScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        val highScore = getItem(position)
        holder.bind(highScore)
    }
}