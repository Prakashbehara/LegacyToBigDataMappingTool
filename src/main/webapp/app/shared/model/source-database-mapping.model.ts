import { ISourceDatabase } from 'app/shared/model//source-database.model';

export const enum FieldDataType {
    STRING = 'STRING',
    NUMBER = 'NUMBER',
    DECIMAL = 'DECIMAL',
    DATE = 'DATE',
    DO_NOT_KNOW = 'DO_NOT_KNOW'
}

export interface ISourceDatabaseMapping {
    id?: number;
    dbColumnName?: string;
    dbDataType?: FieldDataType;
    dbFieldScale?: number;
    dbFieldPrecision?: number;
    sourceDatabase?: ISourceDatabase;
}

export class SourceDatabaseMapping implements ISourceDatabaseMapping {
    constructor(
        public id?: number,
        public dbColumnName?: string,
        public dbDataType?: FieldDataType,
        public dbFieldScale?: number,
        public dbFieldPrecision?: number,
        public sourceDatabase?: ISourceDatabase
    ) {}
}
