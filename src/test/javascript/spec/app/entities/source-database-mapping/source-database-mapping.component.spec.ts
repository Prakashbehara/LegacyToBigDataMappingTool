/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MyAppTestModule } from '../../../test.module';
import { SourceDatabaseMappingComponent } from 'app/entities/source-database-mapping/source-database-mapping.component';
import { SourceDatabaseMappingService } from 'app/entities/source-database-mapping/source-database-mapping.service';
import { SourceDatabaseMapping } from 'app/shared/model/source-database-mapping.model';

describe('Component Tests', () => {
    describe('SourceDatabaseMapping Management Component', () => {
        let comp: SourceDatabaseMappingComponent;
        let fixture: ComponentFixture<SourceDatabaseMappingComponent>;
        let service: SourceDatabaseMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceDatabaseMappingComponent],
                providers: []
            })
                .overrideTemplate(SourceDatabaseMappingComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SourceDatabaseMappingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceDatabaseMappingService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SourceDatabaseMapping(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.sourceDatabaseMappings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
