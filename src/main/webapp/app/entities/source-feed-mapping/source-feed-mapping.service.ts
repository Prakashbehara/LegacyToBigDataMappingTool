import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISourceFeedMapping } from 'app/shared/model/source-feed-mapping.model';

type EntityResponseType = HttpResponse<ISourceFeedMapping>;
type EntityArrayResponseType = HttpResponse<ISourceFeedMapping[]>;

@Injectable({ providedIn: 'root' })
export class SourceFeedMappingService {
    private resourceUrl = SERVER_API_URL + 'api/source-feed-mappings';

    constructor(private http: HttpClient) {}

    create(sourceFeedMapping: ISourceFeedMapping): Observable<EntityResponseType> {
        return this.http.post<ISourceFeedMapping>(this.resourceUrl, sourceFeedMapping, { observe: 'response' });
    }

    update(sourceFeedMapping: ISourceFeedMapping): Observable<EntityResponseType> {
        return this.http.put<ISourceFeedMapping>(this.resourceUrl, sourceFeedMapping, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISourceFeedMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISourceFeedMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
