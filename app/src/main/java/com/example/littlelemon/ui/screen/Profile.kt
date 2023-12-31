package com.example.littlelemon.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.data.model.User
import com.example.littlelemon.ui.navigation.Destinations
import com.example.littlelemon.ui.theme.app.AppTheme
import com.example.littlelemon.ui.viewmodel.ProfileVM

@Composable
fun ProfileScreen(navController: NavController, profileVm: ProfileVM = viewModel()) {
    val user by profileVm.user.collectAsStateWithLifecycle()
    ProfileUI(user = user) {
        profileVm.logOut()

        navController.popBackStack(Destinations.Home.getRoute(), true)
        navController.navigate(Destinations.OnBoard.getRoute())
    }
}

@Composable
fun ProfileUI(user: User?, logOut: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "little lemon logo",
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
                .height(80.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.user_profile_title),
            modifier = Modifier.padding(0.dp, 16.dp),
            style = AppTheme.typography.subTitle
        )

        UserInfoItem(label = stringResource(R.string.user_profile_first_name_label), info = user?.firstName ?: "")
        UserInfoItem(label = stringResource(R.string.user_profile_last_name_label), info = user?.lastName ?: "")
        UserInfoItem(label = stringResource(R.string.user_profile_email_label), info = user?.email ?: "")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { logOut() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 16.dp),
            colors = ButtonDefaults.filledTonalButtonColors(containerColor = AppTheme.color.primary2),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = stringResource(R.string.user_profile_log_out_btn_txt))
        }
    }
}

@Composable
fun UserInfoItem(label: String, info: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        UserInfoLabel(label = label)
        UserInfoText(info = info)
    }
}

@Composable
fun UserInfoLabel(label: String) {
    Text(
        text = label,
        style = AppTheme.typography.sectionTitle,
        color = AppTheme.color.primary1
    )
}

@Composable
fun UserInfoText(info: String) {
    Text(
        text = info,
        style = AppTheme.typography.leadText,
        modifier = Modifier
            .padding(0.dp, 8.dp)
            .fillMaxWidth()
            .border(1.dp, AppTheme.color.primary1, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    ProfileUI(
        User("Test", "Test", "test@example.com")
    ) {}
}