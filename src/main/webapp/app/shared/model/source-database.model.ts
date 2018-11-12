import { IApplication } from 'app/shared/model//application.model';
import { ISourceDatabaseMapping } from 'app/shared/model//source-database-mapping.model';

export const enum TableType {
    FEED_TABLE = 'FEED_TABLE',
    REF_DATA = 'REF_DATA'
}

export interface ISourceDatabase {
    id?: number;
    tableName?: string;
    schema?: string;
    tableType?: TableType;
    application?: IApplication;
    sourceDatabaseMappings?: ISourceDatabaseMapping[];
}

export class SourceDatabase implements ISourceDatabase {
    constructor(
        public id?: number,
        public tableName?: string,
        public schema?: string,
        public tableType?: TableType,
        public application?: IApplication,
        public sourceDatabaseMappings?: ISourceDatabaseMapping[]
    ) {}
}
