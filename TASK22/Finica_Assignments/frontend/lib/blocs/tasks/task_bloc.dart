import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'task_event.dart';
import 'task_state.dart';

class TaskBloc extends Bloc<TaskEvent, TaskState> {
  TaskBloc() : super(TaskInitial()) {
    on<FetchTasks>(_onFetchTasks);
    on<AddTask>(_onAddTask);
  }

  Future<void> _onFetchTasks(FetchTasks event, Emitter<TaskState> emit) async {
    emit(TaskLoading());
    try {
      final prefs = await SharedPreferences.getInstance();
      final token = prefs.getString('jwt');

      final response = await http.get(
        Uri.parse('http://localhost:8080/api/users/tasks'),
        headers: {'Authorization': 'Bearer $token'},
      );

      if (response.statusCode == 200) {
        List<dynamic> data = jsonDecode(response.body);
        List<String> tasks = data.map((t) => t.toString()).toList();
        emit(TaskLoaded(tasks));
      } else {
        emit(TaskError('Failed to load tasks.'));
      }
    } catch (e) {
      emit(TaskError('Error: ${e.toString()}'));
    }
  }

  Future<void> _onAddTask(AddTask event, Emitter<TaskState> emit) async {
    try {
      final prefs = await SharedPreferences.getInstance();
      final token = prefs.getString('jwt');


      final response = await http.post(
        Uri.parse('http://localhost:8080/api/users/tasks'),
        headers: {
          'Authorization': 'Bearer $token',
          'Content-Type': 'application/json',
        },
        body: jsonEncode({"description": event.description}),
      );

      if (response.statusCode == 200 || response.statusCode == 201) {
 
        final updatedResponse = await http.get(
          Uri.parse('http://localhost:8080/api/users/tasks'),
          headers: {'Authorization': 'Bearer $token'},
        );

        if (updatedResponse.statusCode == 200) {
          List<dynamic> data = jsonDecode(updatedResponse.body);
          List<String> tasks = data.map((t) => t.toString()).toList();
          emit(TaskLoaded(tasks));
        } else {
          emit(TaskError('Task added, but failed to refresh list.'));
        }
      } else {
        emit(TaskError('Failed to add task.'));
      }
    } catch (e) {
      emit(TaskError('Error: ${e.toString()}'));
    }
  }
}
