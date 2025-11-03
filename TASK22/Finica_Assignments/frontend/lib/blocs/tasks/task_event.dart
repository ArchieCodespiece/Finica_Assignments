import 'package:equatable/equatable.dart';

abstract class TaskEvent extends Equatable {
  const TaskEvent();

  @override
  List<Object?> get props => [];
}

class FetchTasks extends TaskEvent {}

class AddTask extends TaskEvent {
  final String description;

  const AddTask(this.description);

  @override
  List<Object?> get props => [description];
}
