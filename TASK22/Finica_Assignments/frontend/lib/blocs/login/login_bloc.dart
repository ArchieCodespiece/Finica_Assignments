import 'package:flutter_bloc/flutter_bloc.dart';
import 'login_event.dart';
import 'login_state.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import '../../repositories/auth_repository.dart';

class LoginBloc extends Bloc<LoginEvent, LoginState> {
  final AuthRepository authRepository;

  LoginBloc({required this.authRepository}) : super(LoginInitial()) {
    on<LoginSubmitted>(_onLoginSubmitted);
  }

  Future<void> _onLoginSubmitted(
      LoginSubmitted event, Emitter<LoginState> emit) async {
    emit(LoginLoading());

    try {
  
      final response = await http.post(
        Uri.parse('http://localhost:8080/api/users/login'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({
          'username': event.username,
          'password': event.password,
        }),
      );

      if (response.statusCode == 200) {
        final token = response.body;
        final prefs = await SharedPreferences.getInstance();
        await prefs.setString('jwt', token);

        emit(LoginSuccess(token));
      } else {
        emit(LoginFailure('Invalid username or password'));
      }
    } catch (e) {
      emit(LoginFailure('Login failed. Check your connection.'));
    }
  }
}
