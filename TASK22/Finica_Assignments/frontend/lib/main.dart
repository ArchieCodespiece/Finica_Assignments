import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:task_manager_app/screens/login_screen.dart';
import 'package:task_manager_app/blocs/login/login_bloc.dart';
import 'package:task_manager_app/blocs/tasks/task_bloc.dart'; // ✅ Add this
import 'package:task_manager_app/repositories/auth_repository.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const TaskManagerApp());
}

class TaskManagerApp extends StatelessWidget {
  const TaskManagerApp({super.key});

  @override
  Widget build(BuildContext context) {
    final authRepository = AuthRepository();

    return MultiBlocProvider(
      providers: [
        BlocProvider(
          create: (context) => LoginBloc(authRepository: authRepository),
        ),
        BlocProvider(
          create: (context) => TaskBloc(), // ✅ Added TaskBloc here
        ),
      ],
      child: MaterialApp(
        debugShowCheckedModeBanner: false,
        title: 'Task Manager',
        theme: ThemeData(
          primarySwatch: Colors.indigo,
          scaffoldBackgroundColor: Colors.grey[100],
        ),
        home: const LoginScreen(),
      ),
    );
  }
}
