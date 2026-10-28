# Registration Feature Spec

## Purpose

Create a new user via `POST /api/v1/auth/signup`, persist the JWT token in DataStore, and expose MVI Contract + ViewModel for Registration (UI screen deferred).

## Endpoint

| Method | Path | Auth |
|--------|------|------|
| POST | `/api/v1/auth/signup` | None |

### Request body

```json
{
  "name": "Your Name",
  "email": "user@example.com",
  "password": "YourPassword123",
  "rePassword": "YourPassword123",
  "phone": "01000000000"
}
```

### Response body

Same shape as sign-in:

```json
{
  "message": "success",
  "user": {
    "name": "Ahmed Abd Al-Muti",
    "email": "abdallah312@gmail.com",
    "role": "user"
  },
  "token": "eyJ..."
}
```

## Architecture layers

Clean Architecture (Domain → Data → Presentation), flattened classes, Retrofit + Kotlinx Serialization, Hilt constructor injection, `safeAPICall` from `data/api/SafeApiCall.kt`.

### File list

| Layer | File |
|-------|------|
| Domain entity | `domain/auth/entitiy/AuthUserEntity.kt` (shared) |
| Domain entity | `domain/auth/entitiy/AuthResultEntity.kt` (shared) |
| Domain | `domain/auth/repository/AuthRepository.kt` (shared) |
| Domain | `domain/auth/usecase/RegisterUseCase.kt` |
| Data DTO | `data/auth/model/RegisterRequest.kt` |
| Data DTO | `data/auth/model/AuthUserDto.kt` (shared) |
| Data DTO | `data/auth/model/AuthResponse.kt` (shared) |
| Data | `data/auth/dataSource/api/AuthService.kt` (shared `signup`) |
| Data | `data/auth/dataSource/AuthRemoteDataSourceImpl.kt` |
| Data | `data/auth/dataSource/AuthLocalDataSourceImpl.kt` |
| Data | `data/auth/mapper/AuthMapper.kt` |
| Data | `data/auth/repository/AuthRepositoryImpl.kt` |
| Data | `data/auth/interceptor/AuthInterceptor.kt` |
| DI | `di/auth/AuthModule.kt` |
| Presentation | `peresentation/ui/screens/auth/RegistrationContract.kt` |
| Presentation | `peresentation/ui/screens/auth/RegistrationViewModel.kt` |

## UseCase

- **RegisterUseCase** `(name, email, password, rePassword, phone) → Flow<ApiResult<AuthResultEntity>>`
- Delegates to `AuthRepository.register`
- On success, repository persists `token` via `AuthLocalDataSource`

## Contract (MVI)

### Actions

- `UpdateName(name: String)`
- `UpdateEmail(email: String)`
- `UpdatePassword(password: String)`
- `UpdateRePassword(rePassword: String)`
- `UpdatePhone(phone: String)`
- `SubmitRegister`

### States

- `Idle`
- `Loading`
- `Error(message: String?)`
- `Success(authResult: AuthResultEntity)`

### Events

- `ShowToast(message: String?)`
- `NavigateToHome`

## ViewModel behavior

1. Hold form fields via actions.
2. On `SubmitRegister` → emit `Loading` → collect `RegisterUseCase`.
3. `ApiResult.Success` → `State.Success` + `Event.NavigateToHome`.
4. `ApiResult.Error` → Client/Server → `Event.ShowToast` (same pattern as ProductList / Login).

## Token persistence

- Same DataStore key `auth_token` as Login
- Saved on successful sign-up by `AuthRepositoryImpl`
- `AuthInterceptor` attaches header `token: <jwt>`

## Error handling

Identical to Login: `safeAPICall` maps HTTP/network failures to `ApiResult.Error` / `AppExceptions`.

## Acceptance criteria

- [ ] Sign-up request serializes all five fields correctly
- [ ] Response maps to shared auth entities
- [ ] Token saved to DataStore on success
- [ ] RegistrationViewModel exposes Contract states/events
- [ ] RegistrationScreen deferred

## Out of scope

- RegistrationScreen UI
- Client-side password match validation beyond API
- Forgot password / reset flows
- Logout / clear token
