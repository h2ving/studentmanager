export class Grade {
  id: number;
  course: string;
  date: Date;
  grade: number;

  constructor(id: number, course: string, date: Date, grade: number) {
    this.id = id;
    this.course = course;
    this.date = date;
    this.grade = grade;
  }
}
