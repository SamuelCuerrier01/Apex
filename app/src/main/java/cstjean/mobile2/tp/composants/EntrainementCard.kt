package cstjean.mobile2.tp.composants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cstjean.mobile2.tp.data.Entrainement
import cstjean.mobile2.tp.data.TypeActivite
import cstjean.mobile2.tp.R


@Composable
fun EntrainementCard(
    entrainement: Entrainement,
    modifier: Modifier = Modifier,
    onFavoriteClick: (Entrainement) -> Unit = {},
    onEntrainementClick: (Entrainement) -> Unit = {}
)
{
    Card(
        shape = CutCornerShape(15.dp),
        modifier = modifier.fillMaxWidth().
        shadow(elevation = 15.dp),
        onClick = { onEntrainementClick(entrainement) })
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.training_title_value, entrainement.titre),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Icon(
                imageVector = Icons.Default.Star,
                tint = if (entrainement.estFavoris) Color.Yellow else Color.Gray,
                contentDescription = stringResource(
                    if (entrainement.estExterieur) R.string.outdoor else R.string.indoor
                ),
                modifier = Modifier.size(24.dp)
                    .clickable(onClick = { onFavoriteClick(entrainement)})
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.place_value, entrainement.lieu),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(
                    R.string.activity_type_value,
                    stringResource(entrainement.typeActivite.labelRes)
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

        }
    }
}

@Preview(showBackground = false)
@Preview(showBackground = false,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Composable
fun EntrainementCardPreview() {
    EntrainementCard(
        Entrainement(
            id = 1,
            titre = stringResource(R.string.preview_training_title),
            lieu = stringResource(R.string.preview_place),
            typeActivite = TypeActivite.COURSE,
            estExterieur = true,
            estFavoris = true,
            intensite = 5,
            notesConsignes = stringResource(R.string.preview_notes)
        )
    )
}