/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { SourceDatabaseComponent } from 'app/entities/source-database/source-database.component';
import { SourceDatabaseService } from 'app/entities/source-database/source-database.service';
import { SourceDatabase } from 'app/shared/model/source-database.model';

describe('Component Tests', () => {
    describe('SourceDatabase Management Component', () => {
        let comp: SourceDatabaseComponent;
        let fixture: ComponentFixture<SourceDatabaseComponent>;
        let service: SourceDatabaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceDatabaseComponent],
                providers: []
            })
                .overrideTemplate(SourceDatabaseComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SourceDatabaseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceDatabaseService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SourceDatabase(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.sourceDatabases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
