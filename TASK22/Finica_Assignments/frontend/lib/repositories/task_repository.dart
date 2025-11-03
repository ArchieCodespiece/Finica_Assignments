import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class TaskRepository {
  final String baseUrl = 'http://localhost:8080/api/users';


  Future<List<String>> fetchTasks() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('jwt_token');

    final response = await http.get(
      Uri.parse('$baseUrl/tasks'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode == 200) {
      final data = json.decode(response.body);
      print("Fetched tasks: $data");
      return List<String>.from(data);
    } else {
      throw Exception('Failed to load tasks');
    }
  }

  Future<void> addTask(String description) async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('jwt_token');

    final response = await http.post(
      Uri.parse('$baseUrl/tasks'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer $token',
      },
      body: json.encode({'description': description}),
    );

    if (response.statusCode != 200) {
      throw Exception('Failed to add task');
    }
  }
}
