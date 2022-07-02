export class Course {
  id: number;
  name: string;
  description: string;
  startDate: string;
  endDate: string;
  academicHours: number;
  remote: boolean;
  studentsCount: number;
  usersAssigned?: any[];

  constructor(
    id: number,
    name: string,
    description: string,
    startDate: string,
    endDate: string,
    academicHours: number,
    remote: boolean,
    studensCount: number,
    usersAssigned?: any[]
  ) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.academicHours = academicHours;
    this.remote = remote;
    this.studentsCount = studensCount;
    this.usersAssigned = usersAssigned;
  }
}
