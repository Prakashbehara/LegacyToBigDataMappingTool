import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISourceMapping } from 'app/shared/model/source-mapping.model';

type EntityResponseType = HttpResponse<ISourceMapping>;
type EntityArrayResponseType = HttpResponse<ISourceMapping[]>;

@Injectable({ providedIn: 'root' })
export class SourceMappingService {
    private resourceUrl = SERVER_API_URL + 'api/source-mappings';

    constructor(private http: HttpClient) {}

    create(sourceMapping: ISourceMapping): Observable<EntityResponseType> {
        return this.http.post<ISourceMapping>(this.resourceUrl, sourceMapping, { observe: 'response' });
    }

    update(sourceMapping: ISourceMapping): Observable<EntityResponseType> {
        return this.http.put<ISourceMapping>(this.resourceUrl, sourceMapping, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISourceMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISourceMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
