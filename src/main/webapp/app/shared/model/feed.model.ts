import { IApplication } from 'app/shared/model//application.model';
import { ISourceFeedMapping } from 'app/shared/model//source-feed-mapping.model';

export const enum Frequency {
    DAILY = 'DAILY',
    WEEKLY = 'WEEKLY',
    MONTHEND = 'MONTHEND',
    BIWEEKLY = 'BIWEEKLY',
    ADHOC = 'ADHOC',
    DO_NOT_KNOW = 'DO_NOT_KNOW'
}

export interface IFeed {
    id?: number;
    feedCode?: string;
    fileNamePattern?: string;
    headerCount?: number;
    trailerCount?: number;
    trailerRecordStartsWith?: string;
    feedFrequency?: Frequency;
    sourceDatabaseTableName?: string;
    application?: IApplication;
    sourceMappings?: ISourceFeedMapping[];
}

export class Feed implements IFeed {
    constructor(
        public id?: number,
        public feedCode?: string,
        public fileNamePattern?: string,
        public headerCount?: number,
        public trailerCount?: number,
        public trailerRecordStartsWith?: string,
        public feedFrequency?: Frequency,
        public sourceDatabaseTableName?: string,
        public application?: IApplication,
        public sourceMappings?: ISourceFeedMapping[]
    ) {}
}
