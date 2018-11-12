export interface IDepartment {
    id?: number;
}

export class Department implements IDepartment {
    constructor(public id?: number) {}
}
