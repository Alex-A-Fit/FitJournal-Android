import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.fitjournal.library.presentation.screen.library.components.ExerciseItem
import com.example.fitjournal.theme.Spacing

@Composable
fun LibraryListSection() {
    val dummyData = listOf("Bench", "Squats", "Lateral Raises", "Elevated Globet Squats")

    LazyColumn() {
        itemsIndexed(dummyData) { index, exercise ->
            ExerciseItem(exercise = exercise)
            if (index != dummyData.lastIndex) {
                HorizontalDivider(
                    thickness = Spacing.spacing1,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
