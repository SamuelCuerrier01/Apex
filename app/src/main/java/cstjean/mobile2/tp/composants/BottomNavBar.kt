package cstjean.mobile2.tp.composants

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.AndroidUiModes
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cstjean.mobile2.tp.R

@Composable
fun BottomNavBar(
    onAccueilClick: () -> Unit,
    onListeRechercheClick: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()

    ) {
        FloatingActionButton(
            onClick = onAccueilClick,
            modifier = Modifier.weight(1f).border(1.dp, Color.Black)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = stringResource(R.string.home)
            )
        }
        FloatingActionButton(
            onClick = onListeRechercheClick,
            modifier = Modifier.weight(1f).border(1.dp, Color.Black)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search)
            )
        }
        FloatingActionButton(
            onClick = onCreateClick,
            modifier = Modifier.weight(1f).border(1.dp, Color.Black)
        ) {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = stringResource(R.string.create)
            )
        }
    }
}

@Preview(showBackground = false)
@Preview(showBackground = false,
    uiMode = AndroidUiModes.UI_MODE_NIGHT_YES)
@Composable
fun BottomNavBarPreview() {
    BottomNavBar(
        onAccueilClick = {},
        onListeRechercheClick = {},
        onCreateClick = {}
    )
}