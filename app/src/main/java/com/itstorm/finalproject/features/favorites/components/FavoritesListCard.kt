package com.itstorm.finalproject.features.favorites.components
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.itstorm.core_domain.models.news.UINews
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.components.ChipCard
import com.itstorm.finalproject.shared.components.SubtitleText
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.Grey44
import com.itstorm.finalproject.shared.ui.theme.White
import com.itstorm.finalproject.shared.utils.categoryToUI

@Composable
fun FavoritesListCard(
    modifier: Modifier = Modifier,
    content: UINews,
    toRemoveList: List<Long>,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Grey44
        ),
        colors = CardDefaults.cardColors(
            containerColor = Black
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier.size(64.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(White),
                painter = rememberAsyncImagePainter(content.previewImagePath),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.width(202.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                SubtitleText(
                    text = content.title,
                    color = White,
                    maxLines = 1,
                )

                ChipCard(
                    text = categoryToUI(content.category)
                )
            }

            IconButton(
                onClick = onToggleFavorite
            ) {
                Icon(
                    painter = if (!toRemoveList.contains(content.id))
                        painterResource(R.drawable.heart)
                    else painterResource(R.drawable.notfavorite),
                    contentDescription = null,
                    tint = White
                )
            }
        }
    }
}