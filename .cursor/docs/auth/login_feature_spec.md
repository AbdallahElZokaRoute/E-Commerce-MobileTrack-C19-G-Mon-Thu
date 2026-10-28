# Login Feature Spec

## Purpose

Authenticate an existing user via `POST /api/v1/auth/signin`, persist the JWT token in DataStore, and expose MVI Contract + ViewModel for the Login screen (UI wiring deferred).

## Endpoint

| Method | Path | Auth |
|--------|------|------|
| POST | `/api/v1/auth/signin` | None |

### Request body

```json
{
  "email": "user@example.com",
  "password": "YourPassword123"
}
```

### Response body

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
| Domain entity | `domain/auth/entitiy/AuthUserEntity.kt` |
| Domain entity | `domain/auth/entitiy/AuthResultEntity.kt` |
| Domain | `domain/auth/repository/AuthRepository.kt` |
| Domain | `domain/auth/usecase/LoginUseCase.kt` |
| Data DTO | `data/auth/model/LoginRequest.kt` |
| Data DTO | `data/auth/model/AuthUserDto.kt` |
| Data DTO | `data/auth/model/AuthResponse.kt` |
| Data | `data/auth/dataSource/api/AuthService.kt` |
| Data | `data/auth/dataSource/AuthRemoteDataSourceImpl.kt` |
| Data | `data/auth/dataSource/AuthLocalDataSourceImpl.kt` |
| Data | `data/auth/mapper/AuthMapper.kt` |
| Data | `data/auth/repository/AuthRepositoryImpl.kt` |
| Data | `data/auth/interceptor/AuthInterceptor.kt` |
| DI | `di/auth/AuthModule.kt` |
| Presentation | `peresentation/ui/screens/auth/LoginContract.kt` |
| Presentation | `peresentation/ui/screens/auth/LoginViewModel.kt` |

## UseCase

- **LoginUseCase** `(email: String, password: String) → Flow<ApiResult<AuthResultEntity>>`
- Delegates to `AuthRepository.login`
- On success, repository persists `token` via `AuthLocalDataSource`

## Contract (MVI)

### Actions

- `UpdateEmail(email: String)`
- `UpdatePassword(password: String)`
- `SubmitLogin`

### States

- `Idle`
- `Loading`
- `Error(message: String?)`
- `Success(authResult: AuthResultEntity)`

### Events

- `ShowToast(message: String?)`
- `NavigateToHome`

## ViewModel behavior

1. Hold form fields in state or via actions.
2. On `SubmitLogin` → emit `Loading` → collect `LoginUseCase`.
3. `ApiResult.Success` → `State.Success` + `Event.NavigateToHome`.
4. `ApiResult.Error` → `ClientException` / `ServerException` → `Event.ShowToast` (same pattern as ProductList).

## Token persistence

- DataStore Preferences key: `auth_token`
- Saved on successful sign-in by `AuthRepositoryImpl`
- `AuthInterceptor` attaches header `token: <jwt>` on subsequent requests

## Error handling

| Source | Handling |
|--------|----------|
| HTTP 2xx + body | Success |
| HTTP 500–599 | `ServerException` → toast |
| Other HTTP errors | `ClientException` (parsed `ErrorModel`) → toast |
| Network/unknown | `UnknownException` |

## Acceptance criteria

- [ ] Sign-in request serializes email + password correctly
- [ ] Response maps to `AuthResultEntity` / `AuthUserEntity`
- [ ] Token saved to DataStore on success
- [ ] LoginViewModel exposes Contract states/events
- [ ] Screen wiring deferred (LoginScreen not required to call ViewModel in this pass)

## Out of scope

- LoginScreen UI wiring
- Forgot password / reset flows
- Logout / clear token
