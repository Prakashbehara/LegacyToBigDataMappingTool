import { IApplication } from 'app/shared/model//application.model';
import { ISourceDatabase } from 'app/shared/model//source-database.model';
import { ISourceFeedMapping } from 'app/shared/model//source-feed-mapping.model';

export const enum Frequency {
    DAILY = 'DAILY',
    WEEKLY = 'WEEKLY',
    MONTHEND = 'MONTHEND',
    BIWEEKLY = 'BIWEEKLY',
    ADHOC = 'ADHOC',
    DO_NOT_KNOW = 'DO_NOT_KNOW'
}

export interface ISourceFeed {
    id?: number;
    feedCode?: string;
    fileNamePattern?: string;
    headerCount?: number;
    trailerCount?: number;
    trailerRecordStartsWith?: string;
    feedFrequency?: Frequency;
    sla?: string;
    application?: IApplication;
    sourceDatabase?: ISourceDatabase;
    sourceFeedMappings?: ISourceFeedMapping[];
}

export class SourceFeed implements ISourceFeed {
    constructor(
        public id?: number,
        public feedCode?: string,
        public fileNamePattern?: string,
        public headerCount?: number,
        public trailerCount?: number,
        public trailerRecordStartsWith?: string,
        public feedFrequency?: Frequency,
        public sla?: string,
        public application?: IApplication,
        public sourceDatabase?: ISourceDatabase,
        public sourceFeedMappings?: ISourceFeedMapping[]
    ) {}
}
