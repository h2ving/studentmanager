export class Course {
  id: number;
  name: string;
  description: string;
  startDate: Date;
  endDate: Date;
  academicHours: number;
  remote: boolean;

  constructor(id: number, name: string, description: string, startDate: Date, endDate: Date, academicHours: number, remote: boolean) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.academicHours = academicHours;
    this.remote = remote;
  }
}
