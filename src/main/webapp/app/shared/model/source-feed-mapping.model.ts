import { ISourceFeed } from 'app/shared/model//source-feed.model';
import { IDataModelMapping } from 'app/shared/model//data-model-mapping.model';
import { ISourceDatabaseMapping } from 'app/shared/model//source-database-mapping.model';
import { IDataAsset } from 'app/shared/model//data-asset.model';

export const enum FieldDataType {
    STRING = 'STRING',
    NUMBER = 'NUMBER',
    DECIMAL = 'DECIMAL',
    DATE = 'DATE',
    DO_NOT_KNOW = 'DO_NOT_KNOW'
}

export const enum DataCategory {
    UDG = 'UDG',
    EDG = 'EDG',
    DO_NOT_KNOW = 'DO_NOT_KNOW'
}

export interface ISourceFeedMapping {
    id?: number;
    fieldName?: string;
    fieldOrderNumber?: number;
    fieldDataType?: FieldDataType;
    fieldScale?: number;
    fieldPrecision?: number;
    cde?: boolean;
    pii?: boolean;
    dataCategory?: DataCategory;
    dataQualityRule?: string;
    sourceFeed?: ISourceFeed;
    dataModelMapping?: IDataModelMapping;
    sourceDatabaseMapping?: ISourceDatabaseMapping;
    dataAssets?: IDataAsset[];
}

export class SourceFeedMapping implements ISourceFeedMapping {
    constructor(
        public id?: number,
        public fieldName?: string,
        public fieldOrderNumber?: number,
        public fieldDataType?: FieldDataType,
        public fieldScale?: number,
        public fieldPrecision?: number,
        public cde?: boolean,
        public pii?: boolean,
        public dataCategory?: DataCategory,
        public dataQualityRule?: string,
        public sourceFeed?: ISourceFeed,
        public dataModelMapping?: IDataModelMapping,
        public sourceDatabaseMapping?: ISourceDatabaseMapping,
        public dataAssets?: IDataAsset[]
    ) {
        this.cde = this.cde || false;
        this.pii = this.pii || false;
    }
}
