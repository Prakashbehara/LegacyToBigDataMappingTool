import { ISourceFeed } from 'app/shared/model//source-feed.model';
import { IDataAsset } from 'app/shared/model//data-asset.model';
import { ISourceDatabase } from 'app/shared/model//source-database.model';

export const enum Domain {
    POLICY = 'POLICY',
    FINANCE = 'FINANCE',
    INGENIUM = 'INGENIUM',
    INVESTMENT = 'INVESTMENT',
    DO_NOT_KNOW = 'DO_NOT_KNOW'
}

export const enum ProjectType {
    CONVERSION = 'CONVERSION',
    NEW = 'NEW',
    DO_NOT_KNOW = 'DO_NOT_KNOW'
}

export interface IApplication {
    id?: number;
    applicationCode?: string;
    domain?: Domain;
    projectType?: ProjectType;
    description?: string;
    owner?: string;
    sourceFeeds?: ISourceFeed[];
    dataAssets?: IDataAsset[];
    sourceDatabases?: ISourceDatabase[];
}

export class Application implements IApplication {
    constructor(
        public id?: number,
        public applicationCode?: string,
        public domain?: Domain,
        public projectType?: ProjectType,
        public description?: string,
        public owner?: string,
        public sourceFeeds?: ISourceFeed[],
        public dataAssets?: IDataAsset[],
        public sourceDatabases?: ISourceDatabase[]
    ) {}
}
