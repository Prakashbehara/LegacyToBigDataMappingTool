import { IApplication } from 'app/shared/model//application.model';
import { ISourceFeed } from 'app/shared/model//source-feed.model';
import { ISourceDatabase } from 'app/shared/model//source-database.model';

export const enum DataAssetType {
    CONTROL_REPORTS = 'CONTROL_REPORTS',
    EXCEPTION_REPORTS = 'EXCEPTION_REPORTS',
    BO_REPORT = 'BO_REPORT',
    TABLEAU_REPORT = 'TABLEAU_REPORT',
    SYSTEM_FEED = 'SYSTEM_FEED',
    ADHOC = 'ADHOC',
    SAS = 'SAS',
    PYTHON = 'PYTHON',
    DO_NOT_KNOW = 'DO_NOT_KNOW'
}

export const enum Frequency {
    DAILY = 'DAILY',
    WEEKLY = 'WEEKLY',
    MONTHEND = 'MONTHEND',
    BIWEEKLY = 'BIWEEKLY',
    ADHOC = 'ADHOC',
    DO_NOT_KNOW = 'DO_NOT_KNOW'
}

export interface IDataAsset {
    id?: number;
    name?: string;
    assetFileName?: string;
    type?: DataAssetType;
    frequency?: Frequency;
    storedProcedureName?: string;
    queryLogic?: any;
    remarks?: string;
    edhAssetName?: string;
    emailDistribution?: string;
    application?: IApplication;
    sourceFeeds?: ISourceFeed[];
    sourceDatabases?: ISourceDatabase[];
}

export class DataAsset implements IDataAsset {
    constructor(
        public id?: number,
        public name?: string,
        public assetFileName?: string,
        public type?: DataAssetType,
        public frequency?: Frequency,
        public storedProcedureName?: string,
        public queryLogic?: any,
        public remarks?: string,
        public edhAssetName?: string,
        public emailDistribution?: string,
        public application?: IApplication,
        public sourceFeeds?: ISourceFeed[],
        public sourceDatabases?: ISourceDatabase[]
    ) {}
}
