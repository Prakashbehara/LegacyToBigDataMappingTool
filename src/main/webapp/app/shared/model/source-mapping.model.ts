import { IFeed } from 'app/shared/model//feed.model';
import { IDataModelMapping } from 'app/shared/model//data-model-mapping.model';
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

export interface ISourceMapping {
    id?: number;
    fieldName?: string;
    fieldOrderNumber?: number;
    fieldDataType?: FieldDataType;
    fieldScale?: number;
    fieldPrecision?: number;
    dbFieldName?: string;
    dbFieldDataType?: FieldDataType;
    dbFieldScale?: number;
    dbFieldPrecision?: number;
    cde?: boolean;
    pii?: boolean;
    dataCategory?: DataCategory;
    dataQualityRule?: string;
    feed?: IFeed;
    dataModelMapping?: IDataModelMapping;
    dataAssets?: IDataAsset[];
}

export class SourceMapping implements ISourceMapping {
    constructor(
        public id?: number,
        public fieldName?: string,
        public fieldOrderNumber?: number,
        public fieldDataType?: FieldDataType,
        public fieldScale?: number,
        public fieldPrecision?: number,
        public dbFieldName?: string,
        public dbFieldDataType?: FieldDataType,
        public dbFieldScale?: number,
        public dbFieldPrecision?: number,
        public cde?: boolean,
        public pii?: boolean,
        public dataCategory?: DataCategory,
        public dataQualityRule?: string,
        public feed?: IFeed,
        public dataModelMapping?: IDataModelMapping,
        public dataAssets?: IDataAsset[]
    ) {
        this.cde = this.cde || false;
        this.pii = this.pii || false;
    }
}
