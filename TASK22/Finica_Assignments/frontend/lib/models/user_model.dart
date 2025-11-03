class UserModel {
  final int? id;
  final String username;
  final String password;
  final List<String> tasks;

  UserModel({
    this.id,
    required this.username,
    required this.password,
    required this.tasks,
  });

  factory UserModel.fromJson(Map<String, dynamic> json) {
    return UserModel(
      id: json['id'],
      username: json['username'],
      password: json['password'],
      tasks: List<String>.from(json['tasks'] ?? []),
    );
  }


  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'username': username,
      'password': password,
      'tasks': tasks,
    };
  }
}
