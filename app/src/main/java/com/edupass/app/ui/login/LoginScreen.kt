package com.edupass.app.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edupass.app.R
import com.edupass.app.ui.theme.EduPassBlue
import com.edupass.app.ui.theme.EduPassCardBorder
import com.edupass.app.ui.theme.EduPassFieldBg
import com.edupass.app.ui.theme.EduPassFieldBorder
import com.edupass.app.ui.theme.EduPassInfoBg
import com.edupass.app.ui.theme.EduPassInfoBorder
import com.edupass.app.ui.theme.EduPassNavy
import com.edupass.app.ui.theme.EduPassScreenBg
import com.edupass.app.ui.theme.EduPassTextMuted
import com.edupass.app.ui.theme.EduPassTextPrimary
import com.edupass.app.ui.theme.EduPassTextSecondary
import com.edupass.app.ui.theme.EduPassTheme
import com.edupass.app.ui.theme.EduPassWhite

enum class UserRole(val displayName: String) {
    STUDENT("Student"),
    FACULTY("Faculty"),
    ADMIN("Admin")
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSignInClick: (
    collegeId: String,
    password: String,
    role: UserRole
) -> Unit = { _, _, _ -> },
    onCollegeAccountClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var collegeId by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var selectedRole by rememberSaveable { mutableStateOf(UserRole.STUDENT) }
    var rememberMe by rememberSaveable { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(EduPassScreenBg)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Top Header Card (EduPass VIIT Brand)
        TopBrandBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 28.dp)
        )

        // 2. Greeting Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Text(
                text = "Welcome back",
                color = EduPassNavy,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Sign in to manage campus permissions.",
                color = EduPassTextSecondary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )
        }

        // 3. Main Form Card
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = EduPassWhite),
            border = BorderStroke(1.dp, EduPassCardBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                Text(
                    text = "Sign in",
                    color = EduPassNavy,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Use your college credentials",
                    color = EduPassTextSecondary,
                    fontSize = 13.5.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // College ID Input
                InputFieldLabel(label = "College ID")
                Spacer(modifier = Modifier.height(8.dp))
                LoginInputField(
                    value = collegeId,
                    onValueChange = { collegeId = it },
                    placeholder = "e.g. 22CSE1047",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Password Input
                InputFieldLabel(label = "Password")
                Spacer(modifier = Modifier.height(8.dp))
                LoginInputField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Enter your password",
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    onImeAction = { focusManager.clearFocus() }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Role Selector Section
                InputFieldLabel(label = "Sign in as")
                Spacer(modifier = Modifier.height(10.dp))
                RoleSelectorRow(
                    selectedRole = selectedRole,
                    onRoleSelected = { selectedRole = it }
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Remember Me & Forgot Password Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { rememberMe = !rememberMe }
                    ) {
                        CustomCheckbox(checked = rememberMe)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Remember me",
                            color = EduPassTextSecondary,
                            fontSize = 13.5.sp
                        )
                    }

                    Text(
                        text = "Forgot password?",
                        color = EduPassNavy,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onForgotPasswordClick() }
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                // Primary Button: "Sign in ->"
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        onSignInClick(collegeId, password, selectedRole)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EduPassBlue,
                        contentColor = EduPassWhite
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Sign in",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "→",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Secondary Button: "Sign in with College Account"
                OutlinedButton(
                    onClick = { onCollegeAccountClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, EduPassCardBorder),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = EduPassWhite,
                        contentColor = EduPassBlue
                    )
                ) {
                    Text(
                        text = "Sign in with College Account",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = EduPassBlue
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Bottom Security Notice Banner
        InfoBanner(
            text = "Your credentials are protected by VIIT.",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// ---------------------------------------------------------------------------
// Reusable Screen Components
// ---------------------------------------------------------------------------

@Composable
private fun TopBrandBar(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = EduPassWhite),
        border = BorderStroke(1.dp, EduPassCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_edupass_logo),
                contentDescription = "VIIT Crest",
                modifier = Modifier.size(38.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "EduPass",
                    color = EduPassNavy,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "VIIT",
                    color = EduPassTextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
private fun InputFieldLabel(label: String) {
    Text(
        text = label,
        color = EduPassNavy,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun LoginInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(EduPassFieldBg, RoundedCornerShape(12.dp))
            .border(1.dp, EduPassFieldBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                color = EduPassTextMuted,
                fontSize = 14.sp
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = EduPassTextPrimary,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Normal
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = { onImeAction() }
            ),
            visualTransformation = visualTransformation,
            singleLine = true,
            cursorBrush = SolidColor(EduPassBlue)
        )
    }
}

@Composable
private fun RoleSelectorRow(
    selectedRole: UserRole,
    onRoleSelected: (UserRole) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserRole.entries.forEach { role ->
            val isSelected = role == selectedRole
            RoleOptionButton(
                role = role,
                isSelected = isSelected,
                onClick = { onRoleSelected(role) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun RoleOptionButton(
    role: UserRole,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) EduPassBlue else EduPassFieldBg
    val borderColor = if (isSelected) EduPassBlue else EduPassFieldBorder
    val textColor = if (isSelected) EduPassWhite else EduPassNavy

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            RadioIndicator(isSelected = isSelected)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = role.displayName,
                color = textColor,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
            )
        }
    }
}

@Composable
private fun RadioIndicator(isSelected: Boolean) {
    val outerColor = if (isSelected) EduPassWhite else EduPassTextMuted
    Box(
        modifier = Modifier
            .size(13.dp)
            .border(1.5.dp, outerColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .background(EduPassWhite, CircleShape)
            )
        }
    }
}

@Composable
private fun CustomCheckbox(checked: Boolean) {
    val borderColor = if (checked) EduPassBlue else EduPassTextMuted
    val bgColor = if (checked) EduPassBlue else EduPassWhite

    Box(
        modifier = Modifier
            .size(16.dp)
            .background(bgColor, RoundedCornerShape(4.dp))
            .border(1.2.dp, borderColor, RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Text(
                text = "✓",
                color = EduPassWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun InfoBanner(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(EduPassInfoBg, RoundedCornerShape(16.dp))
            .border(1.dp, EduPassInfoBorder, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular (i) Icon
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .border(1.5.dp, EduPassBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "i",
                    color = EduPassBlue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = EduPassTextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=390dp,height=844dp,dpi=420")
@Composable
fun LoginScreenPreview() {
    EduPassTheme {
        LoginScreen()
    }
}