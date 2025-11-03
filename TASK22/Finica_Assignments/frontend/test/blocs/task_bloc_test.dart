import 'package:flutter_test/flutter_test.dart';
import 'package:bloc_test/bloc_test.dart';
import 'package:task_manager_app/blocs/tasks/task_bloc.dart';
import 'package:task_manager_app/blocs/tasks/task_event.dart';
import 'package:task_manager_app/blocs/tasks/task_state.dart';

void main() {
  group('TaskBloc', () {
    late TaskBloc taskBloc;

    setUp(() {
      taskBloc = TaskBloc();
    });

    tearDown(() {
      taskBloc.close();
    });

    test('initial state is TaskInitial', () {
      expect(taskBloc.state, TaskInitial());
    });

    blocTest<TaskBloc, TaskState>(
      'emits [TaskLoading, TaskError] when FetchTasks fails (no repo)',
      build: () => TaskBloc(),
      act: (bloc) => bloc.add(FetchTasks()),
      expect: () => [isA<TaskLoading>(), isA<TaskError>()],
    );
  });
}
