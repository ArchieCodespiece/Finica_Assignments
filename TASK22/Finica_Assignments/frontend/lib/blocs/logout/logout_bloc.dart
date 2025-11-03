import 'package:flutter_bloc/flutter_bloc.dart';
import 'logout_event.dart';
import 'logout_state.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class LogoutBloc extends Bloc<LogoutEvent, LogoutState> {
  LogoutBloc() : super(LogoutInitial()) {
    on<LogoutRequested>(_onLogoutRequested);
  }

  Future<void> _onLogoutRequested(
      LogoutRequested event, Emitter<LogoutState> emit) async {
    emit(LogoutLoading());

    try {
      final prefs = await SharedPreferences.getInstance();
      final token = prefs.getString('jwt');

      final response = await http.post(
        Uri.parse('http://localhost:8080/api/users/logout'),
        headers: {'Authorization': 'Bearer $token'},
      );

      if (response.statusCode == 200) {
        await prefs.remove('jwt');
        emit(LogoutSuccess());
      } else {
        emit(LogoutFailure('Logout failed on server'));
      }
    } catch (e) {
      emit(LogoutFailure('Network error during logout'));
    }
  }
}
