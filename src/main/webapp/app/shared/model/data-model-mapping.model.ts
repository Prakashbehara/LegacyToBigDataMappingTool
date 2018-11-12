import { IDataModel } from 'app/shared/model//data-model.model';

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

export interface IDataModelMapping {
    id?: number;
    fieldName?: string;
    fieldDataType?: FieldDataType;
    fieldOrderNumber?: number;
    fieldScale?: number;
    fieldPrecision?: number;
    pii?: boolean;
    dataCategory?: DataCategory;
    dataModel?: IDataModel;
}

export class DataModelMapping implements IDataModelMapping {
    constructor(
        public id?: number,
        public fieldName?: string,
        public fieldDataType?: FieldDataType,
        public fieldOrderNumber?: number,
        public fieldScale?: number,
        public fieldPrecision?: number,
        public pii?: boolean,
        public dataCategory?: DataCategory,
        public dataModel?: IDataModel
    ) {
        this.pii = this.pii || false;
    }
}
